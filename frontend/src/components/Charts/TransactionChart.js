import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid,
  ResponsiveContainer,
} from "recharts";

import { useEffect, useState } from "react";
import { getAllTransactions } from "../../services/transactionService";

function TransactionChart() {

  const [chartData, setChartData] = useState([]);

  useEffect(() => {

    async function loadChart() {

      try {

        const transactions = await getAllTransactions();

        const months = [
          "Jan",
          "Feb",
          "Mar",
          "Apr",
          "May",
          "Jun",
          "Jul",
          "Aug",
          "Sep",
          "Oct",
          "Nov",
          "Dec"
        ];

        const monthlyData = Array(12).fill(0);

        transactions.forEach((transaction) => {

          const date = new Date(transaction.transactionDate);

          const month = date.getMonth();

          monthlyData[month] += Number(transaction.amount);

        });

        const finalData = months.map((month, index) => ({
          month,
          amount: monthlyData[index]
        }));

        setChartData(finalData);

      } catch (error) {

        console.error("Error loading transaction chart:", error);

      }

    }

    loadChart();

  }, []);

  return (

    <ResponsiveContainer width="100%" height={300}>

      <BarChart data={chartData}>

        <CartesianGrid strokeDasharray="3 3" />

        <XAxis dataKey="month" />

        <YAxis />

        <Tooltip />

        <Bar
          dataKey="amount"
          fill="#1E3A8A"
        />

      </BarChart>

    </ResponsiveContainer>

  );

}

export default TransactionChart;