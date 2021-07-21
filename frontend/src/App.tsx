import { AuthContextProvider, LocationService } from '@tmtsoftware/esw-ts'
import { Menu } from 'antd'
import React from 'react'
import { BrowserRouter as Router, Link, Route } from 'react-router-dom'
import 'antd/dist/antd.css'
import { Ra } from './components/pages/Ra'
import { AppConfig } from './config/AppConfig'
import { LocationServiceProvider } from './contexts/LocationServiceContext'
import { useQuery } from './hooks/useQuery'

const basename =
  import.meta.env.NODE_ENV === 'production' ? AppConfig.applicationName : ''

export const App = (): JSX.Element => {
  const { data: locationService, loading, error } = useQuery(LocationService)

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
            </Menu>
            {/*// #add-route */}
            <Route exact path='/' component={Ra} />
            {/*// #add-route */}
          </Router>
        </AuthContextProvider>
      </LocationServiceProvider>
    </div>
  )
}
