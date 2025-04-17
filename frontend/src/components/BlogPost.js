import React from 'react';

const BlogPost = ({ post }) => {
    return (
        <div className="blog-post">
            <h2>{post.title}</h2>
            <p>{post.content}</p>
            <span>{post.author}</span>
            <span>{new Date(post.date).toLocaleDateString()}</span>
        </div>
    );
};

export default BlogPost;