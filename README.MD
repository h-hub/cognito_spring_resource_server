# What is the resource server?

The resource server is the OAuth 2.0 term for your API server. For example, In your company, you might have servers such as user details server, invoices server, order server. These servers implement the use cases of your business.

These resource servers can be secured using AWS Cognito.  
First, the application obtains the access token from Cognito
Then, the token will be included in the request header.
Finally, you can call the resource server

Please visit my blog post to learn more.
