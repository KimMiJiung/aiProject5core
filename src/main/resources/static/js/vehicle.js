document.addEventListener("DOMContentLoaded", () => {

    // 색상 클릭 → 이미지 변경
    const mainImage = document.getElementById("mainImage");
    const colorBtns = document.querySelectorAll(".colorCircle");

    colorBtns.forEach(btn => {
        btn.addEventListener("click", () => {
            const img = btn.dataset.img;
            if(img) mainImage.src = "/5core/images/" + img;
        });
    });


    // 트림 선택 → 화면 가격만 미리보기
    const trimBtns = document.querySelectorAll("input[name='trim']");
    const priceText = document.getElementById("priceText");
    const basePrice = parseInt(priceText.dataset.base);

    trimBtns.forEach(btn => {
        btn.addEventListener("change", () => {

            let add = 0;
            if(btn.value === "Exclusive") add = 2000000;
            if(btn.value === "Prestige")  add = 4000000;

            priceText.innerText = (basePrice + add).toLocaleString();
        });
    });

});
