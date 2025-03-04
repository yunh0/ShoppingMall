document.addEventListener("DOMContentLoaded", function () {
    let categoryOverlay = document.getElementById("categoryOverlay");
    let categoryLink = document.getElementById("categoryLink");
    let closeCategory = document.getElementById("closeCategory");
    let topCategories = document.getElementById("topCategories");

    if (categoryLink && categoryOverlay) {
        categoryLink.addEventListener("click", function (e) {
            e.preventDefault();
            categoryOverlay.style.display = "block";
            loadTopCategories();
        });

        closeCategory.addEventListener("click", function () {
            categoryOverlay.style.display = "none";
        });

        categoryOverlay.addEventListener("click", function (e) {
            if (e.target.id === "categoryOverlay") {
                categoryOverlay.style.display = "none";
            }
        });

        function loadTopCategories() {
            fetch("/api/category/top")
                .then(response => response.json())
                .then(categories => {
                    topCategories.innerHTML = "";

                    categories.forEach(category => {
                        let item = document.createElement("div");
                        item.classList.add("category-item");
                        item.dataset.id = category.categoryId;
                        item.innerText = category.name;
                        item.addEventListener("click", () => toggleSubCategories(category.categoryId, item));
                        topCategories.appendChild(item);
                    });
                })
                .catch(error => console.error("카테고리 API 호출 실패!", error));
        }

        function toggleSubCategories(parentId, parentElement) {
            let existingSubMenu = parentElement.nextElementSibling;

            if (existingSubMenu && existingSubMenu.classList.contains("sub-menu")) {
                existingSubMenu.remove();
                return;
            }

            fetch(`/api/category/${parentId}/sub`)
                .then(response => response.json())
                .then(categories => {
                    if (categories.length === 0) {
                        window.location.href = `/product/category/${parentId}`;
                        return;
                    }

                    let subMenu = document.createElement("div");
                    subMenu.classList.add("sub-menu");

                    categories.forEach(category => {
                        let subItem = document.createElement("div");
                        subItem.classList.add("sub-category-item");
                        subItem.dataset.id = category.categoryId;
                        subItem.innerText = category.name;
                        subItem.addEventListener("click", () => toggleSubCategories(category.categoryId, subItem));
                        subMenu.appendChild(subItem);
                    });

                    parentElement.after(subMenu);
                    subMenu.style.display = "block";
                })
                .catch(error => console.error("카테고리 API 호출 실패!", error));
        }
    }
});
