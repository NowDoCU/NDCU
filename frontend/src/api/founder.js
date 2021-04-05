import { instance } from './index.js'

function registerFounder(founder, success, fail) {
    instance
    .post(`founder`, founder)
    .then(success)
    .catch(fail)
}

function loginFounder(user, success, fail) {
    instance
        .post(`login`, user)
        .then(success)
        .catch(fail);
}

export { registerFounder, loginFounder };