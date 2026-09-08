(function checkAuth() {
    const token = localStorage.getItem('jwtToken');
    const currentPath = window.location.pathname;

    if (!token && !currentPath.endsWith('login.html')) {
        window.location.href = 'login.html';
    }
})();

// Logout Function
function logout() {
    localStorage.clear();
    window.location.href = 'login.html';
}

async function authFetch(url, options = {}) {
    const token = localStorage.getItem('jwtToken');

    const headers = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
        ...options.headers
    };

    const response = await fetch(url, { ...options, headers });

    if (response.status === 401 || response.status === 403) {
        alert("Session expired or Unauthorized! Please login again.");
        logout();
    }

    return response;
}