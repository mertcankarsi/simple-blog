import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getPosts } from "./api";

export default function PostList() {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getPosts()
      .then((res) => {
        setPosts(res.data.content);
        setLoading(false);
      })
      .catch(() => setLoading(false));
  }, []);

  if (loading) return <p>Loading...</p>;
  if (!posts.length) return <p>Not found.</p>;

  return (
    <ul>
      {posts.map((post) => (
        <li key={post.referenceKey}>
          <Link to={`/post/${post.referenceKey}`}>{post.title}</Link>
        </li>
      ))}
    </ul>
  );
}
