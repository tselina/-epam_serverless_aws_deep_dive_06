# task05 AWS Lambda + DynamoDB Integration

```shell
syndicate generate project --name task05
cd task05
syndicate generate config --name "dev" `
    --region "eu-central-1" `
    --bundle_bucket_name "syndicate-education-platform-custom-sandbox-artifacts-sbox02/67d6e834/task05" `
    --prefix "cmtr-67d6e834-" `
    --extended_prefix "true" `
    --tags "course_id:SEP_GL_6,course_type:stm,student_id:67d6e834,type:student" `
    --iam_permissions_boundary "arn:aws:iam::905418349556:policy/eo_role_boundary" `
    --access_key "" `
    --secret_key "" `
    --session_token ""
[System.Environment]::SetEnvironmentVariable('SDCT_CONF', 'C:\projects\serverless\aws\epam_serverless_aws_deep_dive_06\task05\.syndicate-config-dev', [System.EnvironmentVariableTarget]::Process)
```

```shell
syndicate generate lambda `
    --name api_handler `
    --runtime java

syndicate generate meta api_gateway `
    --resource_name task5_api `
    --deploy_stage api `
    --minimum_compression_size 0

syndicate generate meta api_gateway_resource `
    --api_name task5_api `
    --path /events `
    --enable_cors false

syndicate generate meta api_gateway_resource_method	`
    --api_name task5_api `
    --path /events `
    --method POST `
    --integration_type lambda `
    --lambda_name api_handler `
    --authorization_type NONE `
    --api_key_required false
    
syndicate generate meta dynamodb `
    --resource_name Events `
    --hash_key_name id `
    --hash_key_type N `
    --read_capacity 1 `
    --write_capacity 1

    --sort_key_name content `
    --sort_key_type S `

```