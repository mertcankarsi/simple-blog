import "./style.css";
import { getPosts } from "./api.js";

const appDiv = document.querySelector("#app");
const postsList = document.createElement("ul");
postsList.id = "posts-list";
appDiv.appendChild(postsList);

getPosts()
  .then((res) => {
    const posts = res.data.content;
    postsList.innerHTML = "";
    posts.forEach((post) => {
      const li = document.createElement("li");
      li.textContent = post.title || JSON.stringify(post);
      postsList.appendChild(li);
    });
  })
  .catch((err) => {
    postsList.innerHTML = "<li>Postlar yüklenemedi.</li>";
  });
