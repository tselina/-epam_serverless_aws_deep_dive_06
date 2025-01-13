# task08 AWS Lambda + Layers Integration

```shell
syndicate generate project --name task08
cd task08
syndicate generate config --name "dev" `
    --region "eu-central-1" `
    --bundle_bucket_name "syndicate-education-platform-custom-sandbox-artifacts-sbox02/67d6e834/task08" `
    --prefix "cmtr-67d6e834-" `
    --extended_prefix "true" `
    --tags "course_id:SEP_GL_6,course_type:stm,student_id:67d6e834,type:student" `
    --iam_permissions_boundary "arn:aws:iam::905418349556:policy/eo_role_boundary" `
    --access_key "" `
    --secret_key "" `
    --session_token ""
[System.Environment]::SetEnvironmentVariable('SDCT_CONF', 'C:\projects\serverless\aws\epam_serverless_aws_deep_dive_06\task08\.syndicate-config-dev', [System.EnvironmentVariableTarget]::Process)
```

```shell
syndicate generate lambda `
    --name api_handler `
    --runtime java
```
