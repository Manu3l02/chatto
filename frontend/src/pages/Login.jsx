import { useState } from "react";
import { useAuth } from "../context/AuthContext";

export default function Login() {
    const { login, loading, error } = useAuth();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [localError, setLocalError] = useState(null);
    
    const handleSubmit = async (e) => {
        e.preventDefault();
        setLocalError(null);
        try {
            await login(email.trim(), password);
        } catch (err) {
            setLocalError(err.message || 'Login faild');
        }
    };

    return (
      <div className="min-h-screen flex items-center justify-center bg-gray-100 p-4">
        <form onSubmit={handleSubmit} className="w-full max-w-md bg-white p-6 rounded shadow">
          <h1 className="text-2xl font-semibold mb-4">Log-in to Chatto</h1>

          <label className="block mb-2 text-sm">
            Email
            <input
              type="email"
              required
              value={email}
              onChange={e => setEmail(e.target.value)}
              className="w-full mt-1 border rounded px-3 py-2"
            />
          </label>

          <label className="block mb-4 text-sm">
            Password
            <input
              type="password"
              required
              value={password}
              onChange={e => setPassword(e.target.value)}
              className="w-full mt-1 border rounded px-3 py-2"
            />
          </label>

          <button
            type="submit"
            disabled={loading}
            className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 disabled:opacity-60"
          >
            {loading ? 'Connection...' : 'Log-in'}
          </button>
 
          {localError && <p className="text-red-500 mt-3 text-sm">{localError}</p>}
          {!localError && error && <p className="text-red-500 mt-3 text-sm">{error}</p>}
        </form>
      </div>
    );
};