# task03


```shell
syndicate generate project --name task03
cd task03
syndicate generate config --name "dev" `
    --region "eu-central-1" `
    --bundle_bucket_name "syndicate-education-platform-custom-sandbox-artifacts-sbox02/67d6e834/task03" `
    --prefix "cmtr-67d6e834-" `
    --extended_prefix "true" `
    --tags "course_id:SEP_GL_6,course_type:stm,student_id:67d6e834,type:student" `
    --iam_permissions_boundary "arn:aws:iam::905418349556:policy/eo_role_boundary" `
    --access_key "" `
    --secret_key "" `
    --session_token ""

[System.Environment]::SetEnvironmentVariable('SDCT_CONF', 'C:\projects\serverless\aws\epam_serverless_aws_deep_dive_06\task03\.syndicate-config-dev', [System.EnvironmentVariableTarget]::Process)

syndicate generate lambda `
    --name hello_world `
    --runtime java

syndicate generate meta api_gateway `
    --resource_name task3_api `
    --deploy_stage api `
    --minimum_compression_size 0

syndicate generate meta api_gateway_resource `
    --api_name task3_api `
    --path /hello `
    --enable_cors false

syndicate generate meta api_gateway_resource_method	`
    --api_name task3_api `
    --path /hello `
    --method GET `
    --integration_type lambda `
    --lambda_name hello_world `
    --authorization_type NONE `
    --api_key_required false

syndicate create_deploy_target_bucket
syndicate build
syndicate deploy

syndicate build
syndicate update --update_only_types lambda

syndicate clean
```