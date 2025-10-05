import { createContext, useContext, useState, useEffect } from "react";

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
    const [token, setToken] = useState(() => localStorage.getItem('chatto_token') || '');
    const [user, setUser] = useState(() => {
        const u = localStorage.getItem('chatto_user');
        return u ? JSON.parse(u) : null;
    });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (token) localStorage.setItem('chatto_token', token);
        else localStorage.removeItem('chatto_token'); 
    }, [token]);

    useEffect(() => {
        if (user) localStorage.setItem('chatto_user', JSON.stringify(user));
        else localStorage.removeItem('chatto_user');
    }, [user]);

    const login = async (email, password) => {
        setLoading(true);
        setError(null);
        try {
            const res = await fetch('http://localhost:8080/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json'},
                body: JSON.stringify({ email, password})
            });
            if (!res.ok) {
                const txt = await res.text();
                throw new Error(txt || `HTTP ${res.status}`);
            }
            const data = await res.json();
            setToken(data.token);
            setUser(data.user);
            setLoading(false);
            return data;
        } catch (e) {
            setError(e.message || 'Login failed');
            setLoading(false);
            throw e;
        }
    };

    const logout = () => {
        setToken('');
        setUser(null);
    };

    const authHeader = () => (token ? {Authorization: `Bearer ${token}`} : {});

    return (
        <AuthContext.Provider value={{token, user, login, logout, authHeader, loading, error}}>
            {children}
        </AuthContext.Provider>
    );
}

/* eslint-disable react-refresh/only-export-components */
export const useAuth = () => useContext(AuthContext);