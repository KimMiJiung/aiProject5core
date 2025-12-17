document.addEventListener('DOMContentLoaded', () => {
    const wrapper = document.querySelector('.carousel-wrapper');
    const slides = document.querySelectorAll('.carousel-slide');
    const prevBtn = document.querySelector('.prev-btn');
    const nextBtn = document.querySelector('.next-btn');
    const indicatorsContainer = document.querySelector('.carousel-indicators');

    let currentIndex = 0;
    const slideCount = slides.length;

    // 인디케이터 생성
    for (let i = 0; i < slideCount; i++) {
        const dot = document.createElement('span');
        dot.classList.add('indicator-dot');
        if (i === 0) dot.classList.add('active');
        dot.addEventListener('click', () => goToSlide(i));
        indicatorsContainer.appendChild(dot);
    }
    const indicatorDots = document.querySelectorAll('.indicator-dot');

    // 슬라이드 이동 함수
    function updateCarousel() {
        const offset = -currentIndex * 100; // -100%, -200% 등으로 이동
        wrapper.style.transform = `translateX(${offset}%)`;

        // 인디케이터 업데이트
        indicatorDots.forEach((dot, index) => {
            dot.classList.toggle('active', index === currentIndex);
        });
    }

    // 다음 슬라이드로
    function nextSlide() {
        currentIndex = (currentIndex + 1) % slideCount;
        updateCarousel();
    }

    // 이전 슬라이드로
    function prevSlide() {
        currentIndex = (currentIndex - 1 + slideCount) % slideCount;
        updateCarousel();
    }

    // 특정 슬라이드로 이동
    function goToSlide(index) {
        currentIndex = index;
        updateCarousel();
    }

    // 이벤트 리스너 연결
    nextBtn.addEventListener('click', nextSlide);
    prevBtn.addEventListener('click', prevSlide);

    // 자동 재생
    setInterval(nextSlide, 5000); // 5초마다 자동 슬라이드
});