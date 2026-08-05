import { useState } from "react";
import { useNavigate } from "react-router-dom";
import MainLayout from "../../layouts/MainLayout";
import { addAccount } from "../../services/accountService";
import "./CreateAccount.css";

function CreateAccount() {

    const navigate = useNavigate();

    const [account, setAccount] = useState({
        accountNumber: "",
        accountHolderName: "",
        accountType: "Savings",
        balance: "",
        branch: "",
        ifscCode: "",
        status: "ACTIVE"
    });

    function handleChange(e) {

        const { name, value } = e.target;

        setAccount({
            ...account,
            [name]: value
        });

    }

    async function handleSubmit(e) {

        e.preventDefault();

        try {

            await addAccount(account);

            alert("Account Created Successfully");

            navigate("/accounts");

        } catch (error) {

            console.error(error);

            alert(error.message);

        }

    }

    return (

        <MainLayout>

            <div className="create-account">

                <h2>Create New Account</h2>

                <form onSubmit={handleSubmit}>

                    <label>Account Number</label>
                    <input
                        type="text"
                        name="accountNumber"
                        value={account.accountNumber}
                        onChange={handleChange}
                        placeholder="ACC1005"
                        required
                    />

                    <label>Account Holder Name</label>
                    <input
                        type="text"
                        name="accountHolderName"
                        value={account.accountHolderName}
                        onChange={handleChange}
                        placeholder="Enter Account Holder Name"
                        required
                    />

                    <label>Account Type</label>
                    <select
                        name="accountType"
                        value={account.accountType}
                        onChange={handleChange}
                    >
                        <option value="Savings">Savings</option>
                        <option value="Current">Current</option>
                    </select>

                    <label>Initial Balance</label>
                    <input
                        type="number"
                        name="balance"
                        value={account.balance}
                        onChange={handleChange}
                        placeholder="1000"
                        required
                    />

                    <label>Branch</label>
                    <input
                        type="text"
                        name="branch"
                        value={account.branch}
                        onChange={handleChange}
                        placeholder="Hyderabad"
                        required
                    />

                    <label>IFSC Code</label>
                    <input
                        type="text"
                        name="ifscCode"
                        value={account.ifscCode}
                        onChange={handleChange}
                        placeholder="SBIN0001234"
                        required
                    />

                    <label>Status</label>
                    <select
                        name="status"
                        value={account.status}
                        onChange={handleChange}
                    >
                        <option value="ACTIVE">ACTIVE</option>
                        <option value="INACTIVE">INACTIVE</option>
                    </select>

                    <button type="submit">
                        Create Account
                    </button>

                </form>

            </div>

        </MainLayout>

    );

}

export default CreateAccount;