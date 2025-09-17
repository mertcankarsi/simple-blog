import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { getPost } from "./api";
import { useTranslation } from "react-i18next";

export default function PostDetail() {
  const { referenceKey } = useParams();
  const [post, setPost] = useState(null);
  const [loading, setLoading] = useState(true);
  const { t } = useTranslation();

  useEffect(() => {
    getPost(referenceKey)
      .then((res) => {
        setPost(res.data);
        setLoading(false);
      })
      .catch(() => setLoading(false));
  }, [referenceKey]);

  if (loading) return <p>Loading</p>;
  if (!post) return <p>Not found</p>;

  return (
    <div>
      <h1>{post.title}</h1>
      <p>{post.content}</p>
      <Link to="/">Back</Link>
    </div>
  );
}
