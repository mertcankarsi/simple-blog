import axios, { AxiosResponse } from "axios";

export interface Post {
  referenceKey: string;
  title: string;
  content: string;
}

export interface PostListResponse {
  content: Post[];
}

const api = axios.create({
  baseURL: "http://localhost:8080/api",
});

export function getPosts(): Promise<AxiosResponse<PostListResponse>> {
  return api.get("/posts");
}

export function getPost(id: string): Promise<AxiosResponse<Post>> {
  return api.get(`/posts/${id}`);
}

export function createPost(data: Partial<Post>): Promise<AxiosResponse<Post>> {
  return api.post("/posts", data);
}
