import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import PostList from "./PostList";
import PostDetail from "./PostDetail";
import "./style.css";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<PostList />} />
        <Route path="/post/:referenceKey" element={<PostDetail />} />
      </Routes>
    </BrowserRouter>
  );
}
