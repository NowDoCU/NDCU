import { instance } from './index.js'

function registerFounder(founder, success, fail) {
    instance
    .post(`founder`, founder)
    .then(success)
    .catch(fail)
}

function loginFounder(loginInfo, success, fail) {
    instance
        .post(`login`, loginInfo)
        .then(success)
        .catch(fail);
}

export { registerFounder, loginFounder };