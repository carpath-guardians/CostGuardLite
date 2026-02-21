class AppReport extends HTMLElement {
    constructor() {
        super()
        this.attachShadow({ mode: 'open' })

        this._data = null
        this._loading = false
        this._error = null
    }

    set data(value) {
        this._data = value
        this._loading = false
        this._error = null
        this.render()
    }

    set loading(value) {
        this._loading = value
        this.render()
    }

    set error(message) {
        this._error = message
        this._loading = false
        this.render()
    }

    connectedCallback() {
        this.render()
    }

    render() {
        this.shadowRoot.innerHTML = `
      <style>
        :host {
          display: block;
          font-family: system-ui, sans-serif;
        }

        .box {
          background: #f1f5f9;
          padding: 16px;
          border-radius: 12px;
          font-size: 14px;
          white-space: pre-wrap;
          word-break: break-word;
        }

        .title {
          font-weight: 600;
          margin-bottom: 10px;
          color: #0f172a;
        }

        .loading {
          color: #6366f1;
          font-weight: 600;
        }

        .error {
          color: #dc2626;
          font-weight: 600;
        }
      </style>

      <div class="box">
        <div class="title">API Report</div>
        ${
            this._loading
                ? `<div class="loading">Loading...</div>`
                : this._error
                    ? `<div class="error">Error: ${this._error}</div>`
                    : this._data
                        ? JSON.stringify(this._data, null, 2)
                        : 'No data yet...'
        }
      </div>
    `
    }
}

customElements.define('app-report', AppReport)