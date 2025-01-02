# task04 AWS Lambdas & SQS + SNS Integration

```shell
syndicate generate project --name task04
cd task04
syndicate generate config --name "dev" `
    --region "eu-central-1" `
    --bundle_bucket_name "syndicate-education-platform-custom-sandbox-artifacts-sbox02/67d6e834/task04" `
    --prefix "cmtr-67d6e834-" `
    --extended_prefix "true" `
    --tags "course_id:SEP_GL_6,course_type:stm,student_id:67d6e834,type:student" `
    --iam_permissions_boundary "arn:aws:iam::905418349556:policy/eo_role_boundary" `
    --access_key "" `
    --secret_key "" `
    --session_token ""
[System.Environment]::SetEnvironmentVariable('SDCT_CONF', 'C:\projects\serverless\aws\epam_serverless_aws_deep_dive_06\task04\.syndicate-config-dev', [System.EnvironmentVariableTarget]::Process)
```

# SQS Queue
```shell
syndicate generate lambda `
    --name sqs_handler `
    --runtime java

syndicate generate meta sqs_queue `
    --resource_name async_queue `
    --fifo_queue false `
    --visibility_timeout 30 `
    --delay_seconds 0 `
    --maximum_message_size 1024 `
    --message_retention_period 60 `
    --receive_message_wait_time_seconds 20

    --content_based_deduplication false
    --region eu-central-1 
    --max_receive_count 1
    --dead_letter_target_arn arn:aws:sqs:eu-central-1:859465876068:dead-letter-queue 
```

Add to SqsHandler class
```java
@LambdaHandler(
    lambdaName = "sqs_handler",
	roleName = "sqs_handler-role",
//	isPublishVersion = true,
//	aliasName = "${lambdas_alias_name}",
	logsExpiration = RetentionSetting.SYNDICATE_ALIASES_SPECIFIED
)
@SqsTriggerEventSource(
		targetQueue = "async_queue",
		batchSize = 10
)
@DependsOn(
		name = "async_queue",
		resourceType = ResourceType.SQS_QUEUE
)
```
Add 
```json
"sqs:*"
```
to [deployment_resources.json](deployment_resources.json) within "lambda-basic-execution"."policy_content"."Statement"."Action"

Add "AWSLambdaSQSQueueExecutionRole" to "sqs_handler-role"."predefined_policies" in [deployment_resources.json](deployment_resources.json)

Add
```json lines
    "dependencies": [
      {
        "resource_name": "sqs_handler-role",
        "resource_type": "iam_role"
      }
    ]
```
to "async_queue" in [deployment_resources.json](deployment_resources.json)


# SNS Topic
```shell
syndicate generate lambda `
    --name sns_handler `
    --runtime java
syndicate generate meta sns_topic `
    --resource_name lambda_topic `
    --region eu-central-1
```

Add
```json
"sns:*"
```
to [deployment_resources.json](deployment_resources.json) within "lambda-basic-execution"."policy_content"."Statement"."Action"

Add "AmazonSNSRole" to "sns_handler-role"."predefined_policies" in [deployment_resources.json](deployment_resources.json)

Add
```json lines
    "dependencies": [
      {
        "resource_name": "sns_handler-role",
        "resource_type": "iam_role"
      }
    ]
```
to "lambda_topic" in [deployment_resources.json](deployment_resources.json)

```shell
syndicate create_deploy_target_bucket
syndicate build
syndicate deploy

syndicate build
syndicate update --update_only_types lambda
```