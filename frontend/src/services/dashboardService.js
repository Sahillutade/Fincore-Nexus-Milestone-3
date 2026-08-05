import { getAllCustomers } from "./customerService";
import { getAllTransactions } from "./transactionService";
import { getAllAccounts } from "./accountService";

export async function getDashboardStats() {
    const [customers, transactions, accounts] = await Promise.all([
        getAllCustomers(),
        getAllTransactions(),
        getAllAccounts()
    ]);

    return {
        totalCustomers: customers.length,
        totalAccounts: accounts.length,
        totalTransactions: transactions.length,
        customers,
        accounts,
        transactions
    };
}