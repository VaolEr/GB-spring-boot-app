window.onload = function () {
  const githubUrl = "https://github.com/VaolEr/GB-spring-boot-app/tree/dev/";

  const links = document.querySelector("body > zero-md")
  .shadowRoot.querySelectorAll('div > ul > li > a');

  links.forEach(link => {
    if (link.href.includes('.http')) {
      link.href = link.href.replace(document.URL, githubUrl);
    }
  });
}