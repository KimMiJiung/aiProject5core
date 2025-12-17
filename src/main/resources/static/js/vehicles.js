// 폼에서 라디오 버튼 클릭 시 submit
document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("sideForm");

    if(form){
        form.querySelectorAll("input").forEach(input => {
            input.addEventListener("change", () => {
                form.submit();
            });
        });
    }
});