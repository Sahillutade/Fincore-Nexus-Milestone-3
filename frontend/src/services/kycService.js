import axios from "axios";

const API_URL = "http://localhost:8083/kyc";

export const getAllKyc = () => axios.get(`${API_URL}/all`);

export const submitKyc = (data) =>
  axios.post(API_URL, data);

export const verifyKyc = (id, remarks) =>
  axios.put(`${API_URL}/${id}/verify`, null, {
    params: { remarks },
  });

export const rejectKyc = (id, remarks) =>
  axios.put(`${API_URL}/${id}/reject`, null, {
    params: { remarks },
  });

export const getKycById = (id) =>
  axios.get(`${API_URL}/${id}`);