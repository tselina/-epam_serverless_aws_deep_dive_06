package com.task02;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.syndicate.deployment.model.RetentionSetting;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.syndicate.deployment.annotations.lambda.LambdaUrlConfig;
import com.syndicate.deployment.model.RetentionSetting;
import com.syndicate.deployment.model.lambda.url.AuthType;
import com.syndicate.deployment.model.lambda.url.InvokeMode;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;


import java.util.HashMap;
import java.util.Map;

@LambdaHandler(
    lambdaName = "hello_world",
	roleName = "hello_world-role",
//	isPublishVersion = true,
//	aliasName = "${lambdas_alias_name}",
	logsExpiration = RetentionSetting.SYNDICATE_ALIASES_SPECIFIED
)
@LambdaUrlConfig(
		authType = AuthType.NONE,
		invokeMode = InvokeMode.BUFFERED
)
public class HelloWorld implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");

		String path = request.getPath();
		String httpMethod = request.getHttpMethod();

		if ("/hello".equals(path)) {
			response.setStatusCode(200);
			response.setHeaders(headers);
			response.setBody("{\"statusCode\": 200, \"message\": \"Hello from Lambda\"}");
		} else {
			response.setStatusCode(400);
			response.setHeaders(headers);
			response.setBody(String.format("{\"statusCode\": 400, \"message\": \"Bad request syntax or unsupported method. Request path: %s. HTTP method: %s\"}", path, httpMethod));
		}

		return resultMap;
	}
}
