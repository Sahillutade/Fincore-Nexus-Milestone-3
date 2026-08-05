import API from "./api";

export async function getAllTransactions(){

    const response = await fetch(`${API.TRANSACTION}/transactions/all`);

    if(!response.ok)
        throw new Error("Unable to fetch transactions");

    return await response.json();

}