import { getPost } from "./api.js";

const appDiv = document.querySelector("#app");
appDiv.innerHTML = "<p>Yükleniyor...</p>";

// URL'den referenceKey'i al
const referenceKey = window.location.pathname.split("/post/")[1];

if (!referenceKey) {
  appDiv.innerHTML = "<p>Geçersiz post adresi.</p>";
} else {
  getPost(referenceKey)
    .then((res) => {
      const post = res.data;
      appDiv.innerHTML = `
        <h1>${post.title}</h1>
        <p>${post.content}</p>
        <a href="/">← Geri dön</a>
      `;
    })
    .catch(() => {
      appDiv.innerHTML = "<p>Post bulunamadı.</p>";
    });
}
