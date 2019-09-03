import React from 'react'
import PropTypes from 'prop-types'
import MusicPlayer from './musicPlayer'
import PageTemplate from '../util/pageTemplate'


const MusicPlayers = ({musics}) => 
    <PageTemplate>
        <div className="row" >
            {musics.map(music => 
                <MusicPlayer key={music.id} music={music} />)}
        </div>
    </PageTemplate>


MusicPlayers.contextTypes = {
    musics : PropTypes.array,
    page : PropTypes.number
}

export default MusicPlayers