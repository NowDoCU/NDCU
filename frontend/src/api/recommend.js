import { instance2 } from './index.js';

function getRecommendedCommercials(options, success, fail) {
  instance2
    .post('recommend/commercial/', options)
    .then(success)
    .catch(fail)
}

export { getRecommendedCommercials }