import React from 'react';

const Footer = () => {
    return (
        <footer>
            <p>&copy; {new Date().getFullYear()} Plaintext Blog. All rights reserved.</p>
            <nav>
                <a href="/about">About</a>
                <a href="/contact">Contact</a>
            </nav>
        </footer>
    );
};

export default Footer;