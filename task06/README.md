# task06 AWS Lambda + DynamoDB Stream Integration

```shell
syndicate generate project --name task06
cd task06
syndicate generate config --name "dev" `
    --region "eu-central-1" `
    --bundle_bucket_name "syndicate-education-platform-custom-sandbox-artifacts-sbox02/67d6e834/task06" `
    --prefix "cmtr-67d6e834-" `
    --extended_prefix "true" `
    --tags "course_id:SEP_GL_6,course_type:stm,student_id:67d6e834,type:student" `
    --iam_permissions_boundary "arn:aws:iam::905418349556:policy/eo_role_boundary" `
    --access_key "ASIA5FTZDTP2HSBI7CVE" `
    --secret_key "nIcuVwDVVf+m7fxmUIOyoTMJlaSpdCP5w1BcVGyx" `
    --session_token "IQoJb3JpZ2luX2VjEHYaCXVzLWVhc3QtMSJIMEYCIQCNDIGu9jwZKHpQRLlDYzdpjLXaCsqhnYKALXQvSPq+4wIhANpADneaP9d6wNVyYbhH5U0JIbPV9fdF41D18zwgIDd1KuACCF4QABoMOTA1NDE4MzQ5NTU2Igw8qoRFIbe1p97lX1EqvQLE6lKP3wgKCDhRVYkb4HcLTB/l1HgNI6litMSBzQJc7gBoU1MABx/so/B+5+tyNy7GYPoGmiUMcFN/3YoduGqU7b5AYEsACtI4jPVzLJdhLyuNyo37951Nk8YNB7LXzA8yQsWMJGW0K9TMpJx3h/H1qZZH71ikjxopac14Qd0RyKgcDznLrhY07IFAdkykjAweroLJ8YM6YcvKaMZ1XEaLEhqE9A2ZGiwKwtTpXYZl/nVmF+SqZ43hn2/+J8eoD12Z0zpZzL9AaEpprNpFI+2YURZzy88Emvbd1uPN5BxnyjWFzQ3RHhtgkZ4mVw5W2NbPn0eG8Y9h4mi47CbyE0B1OnZ87/gptkLz24B0AYi9SoLIRaycOXdi1SnA3SKZuZpYgUZkfsthjgFbath+2Z0Rl2ZW3DkIy8/HAqsVfzC5zfS7BjqcAVTy57k2Xt0bIxWqaaGJWn6ulS6y+AoDl0efPBNbk4/ctJBz4cJMX+534OP99Qd68MlzW3sbmiaCnE4bz8oB35W/Ms4LviTBplTTZqhrx+p0yUnCTD+dMyNjhBDZk3MLBwdUWCsY3w3IIzQ91MDSicF+Wp85fpiFHKgKFUn2DKmtIgwTZ10Kv4SKmGlspQ/S1SyygScYQTJWUz1taQ=="
[System.Environment]::SetEnvironmentVariable('SDCT_CONF', 'C:\projects\serverless\aws\epam_serverless_aws_deep_dive_06\task06\.syndicate-config-dev', [System.EnvironmentVariableTarget]::Process)
```

```shell
syndicate generate lambda `
    --name audit_producer `
    --runtime java

syndicate generate meta dynamodb `
    --resource_name Configuration `
    --hash_key_name key `
    --hash_key_type S `
    --read_capacity 1 `
    --write_capacity 1

syndicate generate meta dynamodb `
    --resource_name Audit `
    --hash_key_name id `
    --hash_key_type S `
    --read_capacity 1 `
    --write_capacity 1

```