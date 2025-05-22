import "./style.css";
import "./script.js";
import { getPosts } from "./api.js";

window.addEventListener("DOMContentLoaded", () => {
  getPosts()
    .then((response) => {
      const responseData = response.data;
      const posts = responseData.content;
      const output = document.querySelector(".output");
      if (Array.isArray(posts) && posts.length > 0) {
        output.innerHTML =
          "<b>Postlar:</b><br>" +
          posts
            .map(
              (post, i) =>
                `${i + 1}. <b>${post.title}</b><br><pre>${
                  post.content
                }</pre><hr>`
            )
            .join("");
      } else {
        output.textContent = "Hiç post yok.";
      }
    })
    .catch((error) => {
      document.querySelector(".output").textContent = "Hata: " + error;
    });
});
