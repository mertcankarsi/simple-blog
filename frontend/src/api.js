import axios from "axios";

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

// Post oluştur
export function createPost(data) {
  return api.post("/posts", data);
}

// Tüm postları getir
export function getPosts() {
  return api.get("/posts");
}

// Diğer endpoint fonksiyonlarını da buraya ekleyebilirsin
// export function getPostById(id) { ... }
// export function updatePost(id, data) { ... }
// export function deletePost(id) { ... }

export default api;
