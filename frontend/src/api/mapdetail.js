import { instance } from "./index.js";

// 상권코드로 데이터 조회
function findAllData(commercialCode, success, fail) {
    instance
        .get(`commercial/${commercialCode}`)
        .then(success)
        .catch(fail);
}

export {
    findAllData,
};