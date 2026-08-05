import BASE_URL from "./api";

export async function getLoans() {
  const response = await fetch(`${BASE_URL}/loans`);
  return response.json();
}