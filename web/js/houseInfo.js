let dummy = [];
let address = "아파트";

let noise_color = { // 0~150 회색, 151 ~ 500  노란색, 500 ~ 빨강
    "low" : "gray",
    "mid" : "#FFC988",
    "high" : "#CC0A0A"
};

function makeDummy() {
    let i = 0;
    while(i < 20){
        let msg = i % 7 === 3 ? "Test" : "";
        dummy.push({
            "id" : i,
            "home_no" : i,
            "message" : msg,
        });
        i += 1;
    }
    return dummy;
}

function fetchHouseList() {
    let house_url = "http://ec2-54-180-24-178.ap-northeast-2.compute.amazonaws.com:8080/api/home/" + address;

    fetch(house_url, {
        method : "GET",
        headers: {
            Accept: 'application/json',
        }
    })
        .then( response => response.json())
        .then( response => {
            console.log(response);
            loadHouseList(response);
            dummy = {response};
        })

}

function loadHouseList(list=dummy) {
    let tbody = document.querySelector("table > tbody");
    let row = document.createElement("tr");

    // 리팩토링 필요
    for (let i = 0; i < list.length; i++){
        row.appendChild(createHouseItem(list[i]));
        if(i % 2 === 1){
            tbody.appendChild(row);
            row = document.createElement("tr");
        }
    }

}

function createHouseItem(item) {
    let houseItem = document.createElement("td");
    let noise_color = document.createElement("div");

    houseItem.classList.add("house-item");
    noise_color.classList.add("noise-color");
    setNoiseColor(noise_color, item.message);

    houseItem.appendChild(noise_color);
    houseItem.appendChild(createRoomBox(item));

    return houseItem;

}

function createRoomBox(item) {
    let room_number_box = document.createElement("div");
    let room_number = document.createElement("div");

    room_number_box.classList.add("room-number-box");
    room_number.classList.add("room-number");

    //색 변경 작업 및 클릭 이벤트 달기
    room_number.textContent = item.home_No;
    room_number_box.appendChild(room_number);
    room_number_box.addEventListener('click', roomClickHandler);
    room_number_box.setAttribute("name", item.message); // 안티패턴
    room_number_box.setAttribute("data-toggle", "modal");
    room_number_box.setAttribute("data-target", "#roomMsg");

    if(item.message !== ''){
        let img = document.createElement("img");
        img.classList.add("room-noti");
        img.setAttribute("src", "../img/msg_noti.png");
        room_number_box.appendChild(img);
    }

    return room_number_box;
}

function setNoiseColor(target, msg) {
    if(msg === "")
        target.style.background = noise_color["low"];
    else
        target.style.background = noise_color["mid"];
}

function roomClickHandler(e) {
    let room_number = e.currentTarget.querySelector(".room-number").textContent;
    let msg = e.currentTarget.getAttribute("name");
    let modal_header = document.querySelector(".modal-header .modal-title");
    let modal_body = document.querySelector(".modal-body");

    modal_header.innerHTML = room_number + "호";
    if(msg === "")
        modal_body.innerHTML = "공고된 메시지가 없습니다.";
    else
        modal_body.innerHTML = msg;

}
