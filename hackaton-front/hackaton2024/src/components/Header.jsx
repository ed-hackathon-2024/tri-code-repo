// src/components/Header.jsx
import React, { useEffect, useRef } from 'react';
import '../components_css/Header.css';
import '../aditional_css/text.css';
import logo from '../assets/logo.png'

const Header = () => {
  const iconMenuRef = useRef(null);
  const menuBodyRef = useRef(null);

  useEffect(() => {
    const handleGotoClick = (e) => {
      const item = e.target;
      if (item.dataset.goto && document.querySelector(item.dataset.goto)) {
        const gotoBlock = document.querySelector(item.dataset.goto);
        const gotoBlockValue =
          gotoBlock.getBoundingClientRect().top +
          window.scrollY -
          document.querySelector("header").offsetHeight - 20;

        if (iconMenuRef.current && iconMenuRef.current.classList.contains("_active")) {
          iconMenuRef.current.classList.remove("_active");
          if (menuBodyRef.current) menuBodyRef.current.classList.remove("_active");
          document.body.classList.remove("_lock");
        }

        window.scrollTo({
          top: gotoBlockValue,
          behavior: "smooth",
        });

        e.preventDefault();
      }
    };

    const gotoElements = document.querySelectorAll("[data-goto]");
    gotoElements.forEach((item) => {
      item.addEventListener("click", handleGotoClick);
    });

    return () => {
      gotoElements.forEach((item) => {
        item.removeEventListener("click", handleGotoClick);
      });
    };
  }, []);

  return (
    <header className="header">
      <div className="header__container _container">
        <a href="#" className="header__logo">
          <img src={logo} alt="Logo" className='logoPic' />
        </a>
        <div className="header__menu menu">
          <div ref={iconMenuRef} className="menu__icon">
            <span></span>
          </div>
          <nav ref={menuBodyRef} className="menu__body">
            <ul className="menu__list">
              <li>
                <a data-goto=".page_section_1" href="#" className="menu__link custome-underline">
                  About
                </a>
              </li>
              <li>
                <a data-goto=".page_section_2" href="#" className="menu__link custome-underline">
                  Features
                </a>
              </li>
              <li>
                <a data-goto=".page_section_3" href="#" className="menu__link custome-underline">
                  Contacts
                </a>
              </li>
              <li>
                <a data-goto=".page_section_4" href="#" className="menu__link custome-underline">
                  Profile
                </a>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </header>
  );
};

export default Header;
