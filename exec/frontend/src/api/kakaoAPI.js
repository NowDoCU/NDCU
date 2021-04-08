/* 
    카카오 관련 API
    - 카카오 맵
    - 카카오 로컬 (좌표 변환)
*/

import axios from 'axios';
import { KAKAO_BASE_URL, KAKAO_REST_KEY } from '../config';

const instance = axios.create({
   baseURL: KAKAO_BASE_URL,
   headers: {
      Authorization: `KakaoAK ${KAKAO_REST_KEY}`,
   },
});

// 법정동 -> 행정동(서버) 코드 변환 (좌표를 기준)
function coordsB2H(center, success, fail) {
   instance
      .get(`geo/coord2regioncode.json?x=${center.La}&y=${center.Ma}`)
      .then(success)
      .catch(fail);
}

export { coordsB2H };
