#!/bin/bash

# Test GET endpoint
echo "Testing GET /posts"
curl -X GET http://localhost:8080/api/posts
echo -e "\n"

# Test POST endpoint
echo "Testing POST /posts"
curl -X POST http://localhost:8080/posts -H "Content-Type: application/json" -d '{"title": "New Post", "content": "This is the content of the new post."}'
echo -e "\n"

# Test PUT endpoint
echo "Testing PUT /posts/1"
curl -X PUT http://localhost:8080/posts/1 -H "Content-Type: application/json" -d '{"title": "Updated Post", "content": "This is the updated content of the post."}'
echo -e "\n"

# Test DELETE endpoint
echo "Testing DELETE /posts/1"
curl -X DELETE http://localhost:8080/posts/1
echo -e "\n"
