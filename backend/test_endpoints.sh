#!/bin/bash

# Test GET endpoint
echo "Testing GET /posts"
curl -X GET http://localhost:8080/api/posts
echo -e "\n"

# Test POST endpoint
echo "Testing POST /posts"
curl -X POST http://localhost:8080/api/posts -H "Content-Type: application/json" -d '{"title": "New Post", "content": "This is the content of the new post."}'
echo -e "\n"

# Test PUT endpoint
echo "Testing PUT /posts/b2c3d4e5-f6a7-8b9c-0d1e-2f3g4h5i6j7k"
curl -X PUT http://localhost:8080/api/posts/b2c3d4e5-f6a7-8b9c-0d1e-2f3g4h5i6j7k -H "Content-Type: application/json" -d '{"title": "Updated Post", "content": "This is the updated content of the post."}'
echo -e "\n"

# Test DELETE endpoint
echo "Testing DELETE /posts/b2c3d4e5-f6a7-8b9c-0d1e-2f3g4h5i6j7k"
curl -X DELETE http://localhost:8080/api/posts/b2c3d4e5-f6a7-8b9c-0d1e-2f3g4h5i6j7k
echo -e "\n"
