# task12 Open API Specification, Documenting

```shell
#Step 1: Set Up the Project
#Don't include syndicate configuration directory named ".syndicate-config-..." and the project state file .syndicate when you copy 'task10' project.
cp -R task10/ task12/
cd task12/

syndicate generate config --name "dev" `
    --region "eu-central-1" `
--bundle_bucket_name "syndicate-education-platform-custom-sandbox-artifacts-sbox02/67d6e834/task12" `
    --prefix "cmtr-67d6e834-" `
--extended_prefix "true" `
    --tags "course_id:SEP_GL_6,course_type:stm,student_id:67d6e834,type:student" `
--iam_permissions_boundary "arn:aws:iam::905418349556:policy/eo_role_boundary" `
    --access_key "" `
--secret_key "" `
--session_token ""
[System.Environment]::SetEnvironmentVariable('SDCT_CONF', 'C:\projects\serverless\aws\epam_serverless_aws_deep_dive_06\task12\.syndicate-config-dev', [System.EnvironmentVariableTarget]::Process)

#Step 4: Build and Deploy Project with the Syndicate Tool:
syndicate build
syndicate deploy

#Step 5: Export API Gateway Resource
syndicate export --resource_type api_gateway --dsl oas_v3
```

## Step 6: Update Deployment Resources

    Open the deployment_resources.json file in the project 'Task 11'.
    Remove the resource named 'task12_api' of type 'api_gateway'. From now your API Gateway will be managed by the syndicate via open API specification.

## Step 7: Update OAS File

Make your API gateway well-documented by editing previously exported OpenAPI specification file:
- Add request and response schemas to the OpenAPI specification.
- Document possible errors thrown.
- Add summary and description to resource methods.


##
syndicate build
syndicate update -resources task12_api

## Step 8: Add an S3 bucket for hosting Swagger UI
```shell
syndicate generate meta s3_bucket --resource_name api-ui-hoster --static_website_hosting True
```

## Step 9: Add Swagger UI Resource
```shell
syndicate generate swagger_ui --name task12_api_ui --path_to_spec path/to/oas_v3.json --target_bucket api-ui-hoster
```

## Step 10: Build and Deploy
```shell
syndicate build
syndicate deploy -resources api-ui-hoster -resources task12_api_ui
```

## Step 11: Test Application

## Step 12: Access Swagger UI
Find the Bucket website endpoint in aws-syndicate deployment logs or by navigating to the S3 Service in the AWS Management Console.
Select the bucket specified for Swagger UI hosting.
Navigate to Properties.
Find the Static website hosting pane (located at the bottom of the page).
Verify API Documentation:
- Check if every API Endpoint (resources & methods) is carefully described.
- Ensure that request & response models, authentication, and possible errors are documented accurately.

