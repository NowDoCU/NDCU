import axios from "axios";
import { API_BASE_URL, API_BASE_URL_2 } from "../config";

const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      "Content-Type": "application/json"
    }
});
  
const instance2 = axios.create({
  baseURL: API_BASE_URL_2,
  headers: {
    "Content-Type": "application/json"
  }
});

export { instance, instance2 };