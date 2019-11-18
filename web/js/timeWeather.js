var constant = {
    WEATHER_API_KEY : "2464393ddf9b26de015e1ef91151e4b1",
    WEATHER_API_URL : "http://api.openweathermap.org/data/2.5/weather",
    DUST_API_KEY : "3FcSYWUQBIhRfYgv2eDYxuog63Wklb0YQxNZNBn1nEsdMCSZBIDdNoHHRDY2L%2BZWI9Quppi05VF2YQMuJpMrcA%3D%3D",
    DUST_API_URL : "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst"
}; // import, export 가 안되네...
var weather_kn = {
    'clear sky' : '맑음',
    'few clouds' : '조금 흐림',
    'scattered clouds' : '흐림',
    'broken clouds' : '매우 흐림',
    'shower rain' : '소나기',
    'rain' : '비',
    'snow' : '눈',
    'mist' : '습함',
};

var sido = '서울';
var region = '안동시';

function printCurrentTime() {
    console.log("printCur");
    let currentDate =new Date();
    let divClock = document.querySelector('.date-time-box');
    let hour = underTenAdapter(currentDate.getHours());
    let min = underTenAdapter(currentDate.getMinutes());
    let time_msg = hour + " : " + min;

    divClock.innerText = time_msg;
    setTimeout(printCurrentTime,10000);
}

function printCurrentDate() {
    let currentDate =new Date();
    let year = currentDate.getFullYear();
    let month = underTenAdapter(currentDate.getMonth()*1 + 1);
    let day = underTenAdapter(currentDate.getDate());
    let divDate = document.querySelector('.date-box');

    divDate.innerText = year + ". " + month + ". " + day;
}

function getTodayWeather() {
    let query_string = {
        'q' : 'Andong',
        'APPID' : constant.WEATHER_API_KEY
    };
    let url_qs = addQueryString(constant.WEATHER_API_URL, query_string);

    fetch(url_qs, {method : "GET"}) // 시간나면 정리하기
        .then( response => { // promise 객체
            return response.json();
        })
        .then(response => {
            let weather_result = document.querySelector('.weather-result');
            let weather_img = document.querySelector('.weather-icon');
            let weather_en = response['weather'][0]['description'];
            let weather_icon_url = "http://openweathermap.org/img/w/" + response['weather'][0]['icon'] + ".png";
            // weather_result.innerHTML = weather_kn[weather_en];
            weather_img.setAttribute("src", weather_icon_url);
        });
}

/* cors 문제 때문에 잠시 보류 */
function getTodayDust() {
    let query_string = {
        'ServiceKey' : constant.DUST_API_KEY,
        'sidoName' : sido,
        'searchCondition' : 'HOUR',
    };
    let url_qs = addQueryString(constant.DUST_API_URL, query_string);
    console.log(url_qs);
    fetch(url_qs, {
        method : "GET"
    })
        .then( response => {
            console.log(response);
            return response.text()
        })
        .then( response => {
            console.log(response);
        })

}

function addQueryString(url, queryString) {
    let rst = url + "?";
    for(let key in queryString){
        rst += key + "=" + queryString[key] + "&";
    }
    return rst;
}

function underTenAdapter(target) {
    return target < 10 ? '0' + target : target;
}
