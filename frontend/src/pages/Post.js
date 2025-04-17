import React from 'react';
import { useParams } from 'react-router-dom';
import BlogPost from '../components/BlogPost';

const Post = () => {
    const { id } = useParams();

    // Here you would typically fetch the post data based on the ID
    // For now, we'll use a placeholder post object
    const post = {
        id: id,
        title: `Post Title ${id}`,
        content: `This is the content of the post with ID ${id}.`
    };

    return (
        <div>
            <h1>{post.title}</h1>
            <BlogPost post={post} />
        </div>
    );
};

export default Post;