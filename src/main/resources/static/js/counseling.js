// 클릭 이벤트가 발생했을 때 실행되는 함수
function toggleContent(headerElement) {
    // 1. 클릭된 헤더 요소에 'active' 클래스를 토글합니다. (화살표 회전 등에 사용)
    headerElement.classList.toggle('active');

    // 2. 헤더의 바로 다음 형제 요소, 즉 콘텐츠 영역을 찾습니다.
    var content = headerElement.nextElementSibling;

    // 3. 콘텐츠의 현재 상태를 확인하여 펼치거나 접습니다.
    if (content.style.maxHeight) {
        // 현재 펼쳐져 있다면 (maxHeight 값이 있다면), 접습니다.
        content.style.maxHeight = null;
    } else {
        // 현재 접혀 있다면, 펼칩니다. 
        // scrollHeight를 사용하여 콘텐츠의 실제 높이만큼 maxHeight를 설정합니다.
        content.style.maxHeight = content.scrollHeight + "px";
    }
}


document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('purchase-plan-form');

    // 기타 사유 입력 활성화/비활성화 처리
    const otherPurposeCheck = document.getElementById('other_purpose_check');
    const otherPurposeText = document.getElementById('other_purpose_text');

    otherPurposeCheck.addEventListener('change', function() {
        otherPurposeText.disabled = !this.checked;
        if (!this.checked) {
            otherPurposeText.value = '';
        }
    });
    
    // 실시간 업데이트 함수
    function updateSummary() {
        // 1. 차량 구매 목적 (체크박스)
        const purposeChecks = form.querySelectorAll('input[name="purpose"]:checked');
        let purposeValues = Array.from(purposeChecks)
            .map(cb => {
                // '기타'가 선택되었고, 텍스트가 입력된 경우 텍스트 반영
                if (cb.value === '기타' && otherPurposeCheck.checked && otherPurposeText.value.trim() !== '') {
                    return `기타 (${otherPurposeText.value.trim()})`;
                }
                return cb.value;
            })
            // '기타' 체크박스만 선택되고 텍스트가 빈 경우, '기타'를 제외
            .filter(val => val !== '기타' || (val === '기타' && otherPurposeText.value.trim() !== ''));

        if (purposeValues.length > 0) {
            document.getElementById('summary-purpose').textContent = `구매 목적: ${purposeValues.join(', ')}`;
        } else {
            document.getElementById('summary-purpose').textContent = '구매 목적: 선택 안 함';
        }

        // 2. 희망 구매 시점 (라디오 버튼)
        const timeRadio = form.querySelector('input[name="purchase_time"]:checked');
        document.getElementById('summary-time').textContent = `희망 시점: ${timeRadio ? timeRadio.value : '미정'}`;

        // 3. 선호 차량 유형 (체크박스)
        const typeChecks = form.querySelectorAll('input[name="car_type"]:checked');
        const typeValues = Array.from(typeChecks).map(cb => cb.value);
        document.getElementById('summary-type').textContent = `차량 유형: ${typeValues.length > 0 ? typeValues.join(', ') : '선택 안 함'}`;

        // 4. 선호 엔진 (체크박스)
        const engineChecks = form.querySelectorAll('input[name="engine_type"]:checked');
        const engineValues = Array.from(engineChecks).map(cb => cb.value);
        document.getElementById('summary-engine').textContent = `선호 엔진: ${engineValues.length > 0 ? engineValues.join(', ') : '선택 안 함'}`;
    }

    // 폼 요소의 변경 이벤트에 리스너 추가
    form.addEventListener('change', updateSummary);
    otherPurposeText.addEventListener('input', updateSummary); // 기타 텍스트 입력 시에도 업데이트

    // 초기 로드 시 한 번 실행
    updateSummary();
});