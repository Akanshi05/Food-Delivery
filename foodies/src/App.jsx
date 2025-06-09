import React, { useContext } from 'react';
import Menubar from './components/Menubar';
import { Route, Routes } from 'react-router-dom';
import Home from './pages/Home/Home'
import Contact from './pages/Contact/Contact';
import ExploreFood from './pages/ExploreFood/ExploreFood';
import FoodDetails from './pages/FoodDetails/FoodDetails';
import Cart from './pages/Cart/Cart';
import PlaceOrder from './pages/PlaceOrder/PlaceOrder';
import Login from './components/Login';
import Register from './components/Register';
import { ToastContainer } from 'react-toastify';
import MyOrders from './pages/MyOrders/MyOrders';
import { StoreContext } from './context/StoreContext';

const App = () => {

  const {token} = useContext(StoreContext);

  return (
    <div>
      <Menubar/>
      <ToastContainer/>
      <Routes>
        <Route>
          <Route path='/' element={<Home />}></Route>
          <Route path='/contact' element={<Contact />}></Route>
          <Route path='/explore' element={<ExploreFood />}></Route>
          <Route path='/food/:id' element={<FoodDetails />}></Route>
          <Route path='/cart' element={<Cart/>}></Route>
          <Route path='/order' element={token ? <PlaceOrder/> : <Login/>}></Route>
          <Route path='/login' element={token ? <Home/> : <Login/>}></Route>
          <Route path='/register' element={token ? <Home/> : <Register/>}></Route>
          <Route path='/myorders' element={token ? <MyOrders/> : <Login/>}></Route>
        </Route>
      </Routes>
    </div>
  )
}

export default App;