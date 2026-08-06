import axios from "axios";

const API_URL = "http://localhost:8084/payment";

export const getAllPayments = () =>
  axios.get(`${API_URL}/all`);

export const getAllBeneficiaries = () =>
  axios.get(`${API_URL}/beneficiary/all`);

export const addBeneficiary = (data) =>
  axios.post(`${API_URL}/beneficiary/add`, data);

export const makeTransfer = (data) =>
  axios.post(`${API_URL}/transfer`, data);

export const createRazorpayOrder = (amount) =>
  axios.post(`${API_URL}/create-order`, {
    amount,
  });