import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080/api",
});

export function getPosts() {
  return api.get("/posts");
}

export function createPost(data) {
  return api.post("/posts", data);
}
