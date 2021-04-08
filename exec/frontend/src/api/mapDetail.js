import { instance } from './index.js';

// 상권코드로 상권 세부 정보(Detail) 조회
function findAllData(commercialCode, success, fail) {
   instance
      .get(`commercial/${commercialCode}`)
      .then(success)
      .catch(fail);
}

// 행정동 코드로 해당 동에 포함된 상권 조회
function findDongData(emd_cd, success, fail) {
   instance
      .get(`commercial/list/${emd_cd}`)
      .then(success)
      .catch(fail);
}

// 전체 구 폴리곤, 중심 좌표 정보 조회
function findAllGu(success, fail) {
   instance
      .get(`gu`)
      .then(success)
      .catch(fail);
}

export { findAllData, findDongData, findAllGu };
