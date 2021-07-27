import React from 'react'
import { Route, Switch } from 'react-router-dom'
import { NotFound } from '../components/error/NotFound'
import { SecuredRa } from '../components/pages/SecuredRa'
import { Ra } from '../db/Ra'
import { ProtectedRoute } from './ProtectedRoute'

export const Routes = (): JSX.Element => {
  return (
    <Switch>
      {/*// #add-route */}
      <Route exact path='/' component={Ra} />
      {/*// #add-route */}
      {/*// #add-protected-route */}
      <ProtectedRoute path='/securedRa' component={SecuredRa} />
      {/*// #add-protected-route */}
      <Route path='*' component={NotFound} />
    </Switch>
  )
}