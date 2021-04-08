import { instance } from './index.js'

function createBookmark(commercialCode, success, fail) {
    let token = window.localStorage.getItem('accessToken');
    const config = { headers: { Authorization: `Bearer ${token}` } };
    
    instance
        .post(`bookmark`, commercialCode, config)
        .then(success)
        .catch(fail);
}

function getBookmarkList(success, fail) {
    let token = window.localStorage.getItem('accessToken');
    const config = { headers: { Authorization: `Bearer ${token}` } };

    instance
        .get(`bookmark`, config)
        .then(success)
        .catch(fail);
}

function removeBookmark(commercialCode, success, fail) {
    let token = window.localStorage.getItem('accessToken');
    const config = { headers: { Authorization: `Bearer ${token}` } };

    instance
    .delete(`bookmark/${commercialCode}`, config)
    .then(success)
    .catch(fail);
}

export { getBookmarkList, removeBookmark, createBookmark };