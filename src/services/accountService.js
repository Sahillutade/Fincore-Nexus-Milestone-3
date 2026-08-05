import API from "./api";

const ACCOUNT_URL = `${API.ACCOUNT}/account`;

// Get all accounts
export const getAllAccounts = async () => {
  const response = await fetch(`${ACCOUNT_URL}/all`);

  if (!response.ok) {
    throw new Error("Unable to fetch accounts");
  }

  return response.json();
};

// Add account
export const addAccount = async (account) => {
  const response = await fetch(`${ACCOUNT_URL}/add`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(account),
  });

  if (!response.ok) {
    throw new Error(await response.text());
  }

  return response.json();
};