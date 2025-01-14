# task10 Serverless API + Cognito Integration

```shell
syndicate generate project --name task10
cd task10
syndicate generate config --name "dev" `
    --region "eu-central-1" `
    --bundle_bucket_name "syndicate-education-platform-custom-sandbox-artifacts-sbox02/67d6e834/task10" `
    --prefix "cmtr-67d6e834-" `
    --extended_prefix "true" `
    --tags "course_id:SEP_GL_6,course_type:stm,student_id:67d6e834,type:student" `
    --iam_permissions_boundary "arn:aws:iam::905418349556:policy/eo_role_boundary" `
    --access_key "" `
    --secret_key "" `
    --session_token ""
[System.Environment]::SetEnvironmentVariable('SDCT_CONF', 'C:\projects\serverless\aws\epam_serverless_aws_deep_dive_06\task10\.syndicate-config-dev', [System.EnvironmentVariableTarget]::Process)
```

```shell
syndicate generate lambda `
    --name api_handler `
    --runtime java

#,
#            "dynamodb:Scan",
#            "cognito-idp:DescribeUserPool",
#            "cognito-idp:GetUser",
#            "cognito-idp:ListUsers",
#            "cognito-idp:AdminCreateUser",
#            "cognito-idp:AdminInitiateAuth",
#            "cognito-idp:GetIdentityProviderByIdentifier",
#            "cognito-idp:ListUserPools",
#            "cognito-idp:ListUserPoolClients",
#            "cognito-idp:AdminRespondToAuthChallenge",
#            "cognito-idp:AdminConfirmSignUp"
#
#



#Step 4. Generate Cognito Metadata
syndicate generate meta cognito_user_pool --resource_name simple-booking-userpool
#"client": {
#      "client_name": "client-app",
#      "generate_secret": false,
#      "explicit_auth_flows": [
#        "ALLOW_ADMIN_USER_PASSWORD_AUTH",
#        "ALLOW_CUSTOM_AUTH",
#        "ALLOW_USER_SRP_AUTH",
#        "ALLOW_REFRESH_TOKEN_AUTH"
#      ]
#    }
#  }

#Step 5. Generate API Gateway metadata
syndicate generate meta api_gateway --resource_name task10_api --deploy_stage api

#Generate API Gateway authorizer metadata
syndicate generate meta api_gateway_authorizer --api_name task10_api --name authorizer --type COGNITO_USER_POOLS --provider_name simple-booking-userpool

#Generate API Gateway resources(paths) and methods metadata
#signin
syndicate generate meta api_gateway_resource --api_name task10_api --path /signin --enable_cors true
syndicate generate meta api_gateway_resource_method --api_name task10_api --path /signin --method POST --integration_type lambda --lambda_name api_handler

#signup
syndicate generate meta api_gateway_resource --api_name task10_api --path /signup --enable_cors true
syndicate generate meta api_gateway_resource_method --api_name task10_api --path /signup --method POST --integration_type lambda --lambda_name api_handler

#tables
syndicate generate meta api_gateway_resource --api_name task10_api --path /tables --enable_cors true
syndicate generate meta api_gateway_resource_method --api_name task10_api --path /tables --method GET --integration_type lambda --lambda_name api_handler --authorization_type CUSTOM --authorizer_name authorizer
syndicate generate meta api_gateway_resource_method --api_name task10_api --path /tables --method POST --integration_type lambda --lambda_name api_handler --authorization_type CUSTOM --authorizer_name authorizer

#tables/{tableId}
syndicate generate meta api_gateway_resource --api_name task10_api --path /tables/{tableId} --enable_cors true
syndicate generate meta api_gateway_resource_method --api_name task10_api --path /tables/{tableId} --method GET --integration_type lambda --lambda_name api_handler --authorization_type CUSTOM --authorizer_name authorizer



#reservations
syndicate generate meta api_gateway_resource --api_name task10_api --path /reservations --enable_cors true
syndicate generate meta api_gateway_resource_method --api_name task10_api --path /reservations --method GET --integration_type lambda --lambda_name api_handler --authorization_type CUSTOM --authorizer_name authorizer
syndicate generate meta api_gateway_resource_method --api_name task10_api --path /reservations --method POST --integration_type lambda --lambda_name api_handler --authorization_type CUSTOM --authorizer_name authorizer


#Step 6. Generate DynamoDB Table Metadata:
#Tables
syndicate generate meta dynamodb --resource_name Tables --hash_key_name id --hash_key_type S

#Reservations
syndicate generate meta dynamodb --resource_name Reservations --hash_key_name id --hash_key_type S
```

#Step 7. Configure syndicate aliases:
lambdas_alias_name: learn
tables_table: Tables
reservations_table: Reservations
booking_userpool: simple-booking-userpool

