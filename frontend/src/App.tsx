import { AuthContextProvider, LocationService } from '@tmtsoftware/esw-ts'
import { Menu } from 'antd'
import React from 'react'
import { BrowserRouter as Router, Link, Route } from 'react-router-dom'
import 'antd/dist/antd.css'
import { Ra } from './components/pages/Ra'
import { SecuredRa } from './components/pages/SecuredRa'
import { AppConfig } from './config/AppConfig'
import { LocationServiceProvider } from './contexts/LocationServiceContext'
import { useAuth } from './hooks/useAuth'
import { useQuery } from './hooks/useQuery'
import { ProtectedRoute } from './routes/ProtectedRoute'
import { Logout } from './components/menu/Logout'
import { Login } from './components/menu/Login'

const basename =
  import.meta.env.NODE_ENV === 'production' ? AppConfig.applicationName : ''

export const App = (): JSX.Element => {
  const { data: locationService, loading, error } = useQuery(LocationService)

  // #use-auth-hook
  const { auth, login, logout } = useAuth()
  const isAuthenticated = auth?.isAuthenticated() ?? false
  // #use-auth-hook

  if (loading) return <div>Loading...</div>
  if (error || !locationService)
    return <div>Location Service not Available, reason {error?.message}</div>

  return (
    <div>
      <LocationServiceProvider locationService={locationService}>
        <AuthContextProvider>
          <Router basename={basename}>
            <Menu mode='horizontal'>
              {/*// #add-route-action */}
              <Menu.Item key='ra'>
                <Link to='/'>Ra to String</Link>
              </Menu.Item>
              {/*// #add-route-action */}
              {/*// #add-protected-route-action */}
              <Menu.Item key='securedRa'>
                <Link to='/securedRa'>Ra to String</Link>
              </Menu.Item>
              {/*// #add-protected-route-action */}
              {/*// #add-login-logout */}
              {isAuthenticated ? (
                <Logout logout={logout} />
              ) : (
                <Login login={login} />
              )}
              {/*// #add-login-logout */}
            </Menu>
            {/*// #add-route */}
            <Route exact path='/' component={Ra} />
            {/*// #add-route */}
            {/*// #add-protected-route */}
            <ProtectedRoute path='/securedRa' component={SecuredRa} />
            {/*// #add-protected-route */}
          </Router>
        </AuthContextProvider>
      </LocationServiceProvider>
    </div>
  )
}
